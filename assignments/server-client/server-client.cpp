#include <iostream>
#include <thread>
#include <condition_variable>
#include <mutex>
#include <cstdlib>
#include <bits/stdc++.h>
#include <chrono>

using namespace std;

std::mutex mutexLock;
std::condition_variable conditionVariable;

bool turn = false;             // to change turn between client and server
int currentClientNumber = 0;   // current running client
int totalActiveClients = 0;    // to keep track count of current client
vector<int> activeClients;     // to track of active clients to fetch next one
vector<int> buffer;            // buffer to transfer data between client and server
vector<queue<int>> clientData; // Store client data at first step

// Print client queue (not used in actual logic)
void printQueue(queue<int> q)
{
    while (!q.empty())
    {
        std::cout << q.front() << " ";
        q.pop();
    }
    cout << endl;
}

// Print shared buffer
void printBuffer()
{
    for (int i = 0; i < buffer.size(); i++)
    {
        std::cout << buffer.at(i) << (i == (buffer.size() - 1) ? "" : ",");
    }
    cout << endl;
}

// Empty buffer after client consumes after sorting
void emptyBuffer()
{
    int n = buffer.size();
    for (int i = 0; i < n; i++)
    {
        buffer.pop_back();
    }
}

// Get next client to be processed
int getNextClient(int currentClient)
{
    if (activeClients.size() == 1)
    {
        return activeClients.at(0);
    }
    else if (currentClient == activeClients.back())
    {
        return activeClients.front();
    }
    else
    {
        for (auto it = activeClients.begin(); it != activeClients.end(); ++it)
        {
            if (*it == currentClient)
            {
                return *(it + 1);
            }
        }
    }
    return -1;
}

// Function to produce client data
void clientProduceData(int workingClient)
{
    // std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    for (int j = 0; j < 5 && clientData.at(workingClient).size() > 0; j++)
    {
        buffer.push_back(clientData.at(workingClient).front());
        clientData.at(workingClient).pop();
    }
    // std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    std::cout << "-----------------------------------------------------------------------------" << endl;
    std::cout << "Client " << workingClient + 1 << " produce data    ---> ";
    printBuffer();
}

// Function to consume data for client
void clientConsumeData(int clientThread)
{
    cout << "Client " << clientThread + 1 << " consume data    ---> ";
    printBuffer();
    emptyBuffer(); // Release data in buffer to unlock next client
    std::cout << "-----------------------------------------------------------------------------" << endl
              << endl
              << endl;
}

// Function to consume data for server
void serverConsumeData()
{
    std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    std::sort(buffer.begin(), buffer.end());
    std::cout << "Server sorted the data   ---> ";
    printBuffer();
}

// Actual client function with lock
void client(int clientThread)
{
    while (true)
    {
        std::unique_lock<std::mutex> ul(mutexLock); // aquire lock to precess shared resources
        conditionVariable.wait(ul, [&clientThread]()
                               { return turn == false && currentClientNumber == clientThread; }); // make thread wait in queue
        // client can consume data
        if (buffer.size() != 0)
        {
            clientConsumeData(clientThread);
            currentClientNumber = getNextClient(currentClientNumber);
        }
        // client can produce data
        else if (clientData.at(clientThread).size() != 0)
        {
            // std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            clientProduceData(clientThread);
            turn = true;
        }
        // client data is finished
        else
        {
            ul.unlock();
            totalActiveClients--;
            if (totalActiveClients == 0)
            {
                turn = true;
            }
            int nextClientNumber = getNextClient(currentClientNumber); // get next client to unlock it's thread
            activeClients.erase(std::remove(activeClients.begin(), activeClients.end(), currentClientNumber), activeClients.end());
            currentClientNumber = nextClientNumber;
            conditionVariable.notify_all();
            break; // stop thread
        }
        ul.unlock();
        conditionVariable.notify_all();
        ul.lock();
    }
    cout << "00000000000000000000000000000  End of Client " << clientThread + 1 << "  00000000000000000000000000000" << endl
         << endl
         << endl;
}

// Actual server function with lock
void server()
{
    while (true)
    {
        std::unique_lock<std::mutex> ul(mutexLock); // aquire lock
        conditionVariable.wait(ul, []()
                               { return turn; }); // condition variable to make thread wait in queue
        // all producer finished so server can end
        if (buffer.size() == 0)
        {
            ul.unlock();
            conditionVariable.notify_all();
            break; // End server thread
        }
        turn = false;
        serverConsumeData(); // server can consume data
        ul.unlock();
        conditionVariable.notify_all();
    }
    cout << "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx  End of Server    xxxxxxxxxxxxxxxxxxxxxxxxxxxxx" << endl;
}

int main()
{
    int numberOfClients;
    cout << "\nEnter number of Clients                           -->  "; // take custom number of clients
    cin >> numberOfClients;
    for (int i = 0; i < numberOfClients; i++)
    {
        queue<int> oneClientData;
        int clientDataLength;
        cout << "Enter number of data inputs for          client " << i + 1 << " -->  ";
        cin >> clientDataLength;
        cout << "Enter actual data inputs for             client " << i + 1 << "  ::  ";
        for (int j = 0; j < clientDataLength; j++)
        {
            int temp;
            cin >> temp;
            oneClientData.push(temp);
        }
        clientData.push_back(oneClientData);
        // printQueue(oneClientData);
    }
    std::vector<std::thread> clients; // to maintain client threads to join later
    for (int i = 0; i < numberOfClients; i++)
    {
        totalActiveClients++;
        activeClients.push_back(i);
        clients.push_back(std::thread(client, i));
    }
    std::thread c1(server);
    for (auto &t : clients)
    {
        t.join(); // finish all client threads
    }
    c1.join(); // finish server thread
    return 0;
}