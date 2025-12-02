import Message from "./Message";

const Messages = ({ messages, onDelete, showInfo }) => {
  return (
      <div>
        <table style={{width: "100%", border: "solid"}} >
          <thead>
          <tr>
            <th style={{width: "50%"}}>Message</th>
            <th style={{width: "50%"}}> Action</th>
          </tr>
          </thead>
          <tbody>
            {messages.map((message, index) => (
                <Message key={index} message={message} onDelete={onDelete} showInfo={showInfo} />
            ))}
          </tbody>
        </table>

      </div>
  )
}

export default Messages