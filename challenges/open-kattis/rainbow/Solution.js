const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
  })
  
const read = (str) => new Promise(resolve => readline.question(str, resolve));
let isGood = [];
let edges = {};
const routes = {};

function visit(start, ends, parent, isBad, path) {
    for (let i = 0; i < ends.length; i++) {
        if(ends[i] != parent) {    
            if(isBad) {
                isGood[ends[i]] = false;
                visit(ends[i], routes[ends[i]], start, true, path + "-" + ends[i]);
            } else {
                if(edges[start+""+ends[i]] == edges[parent+""+start]) {
                    isGood[ends[i]] = false;
                    // console.log(path + ends[i])
                    let splitPath = path.split("-");
                    for (let j = 0; j < splitPath.length-1; j++) {
                        isGood[parseInt(splitPath[j])] = false;
                    }
                    visit(ends[i], routes[ends[i]], start, true, path + "-" + ends[i]);
                } else {
                    visit(ends[i], routes[ends[i]], start, false, path + "-" + ends[i]);
                }
            }
        }
    }
}
function run() {
    let fl = true;
    let n = 0;
    let visited = [];
    let j = 1;
    readline.on('line', (line) => {
        if(fl == true) {
            n += line;
            fl = false;
            for (let i = 0; i < n; i++) {
                visited[i] = false;
                isGood[i] = true;        
            }
        } else {
            let temp = line.split(" ");
            let start = parseInt(temp[0]), end = parseInt(temp[1]), weight = parseInt(temp[2]);
            edges[temp[0]+temp[1]] = weight;
            edges[temp[1]+temp[0]] = weight;
            if(!routes[start]) {
                routes[start] = [];
            }
            routes[start].push(end);
            if(!routes[end]) {
                routes[end] = [];
            }
            routes[end].push(start);
            j++;
            // console.log(j)
            if(j == (n)) {
                // console.log((routes));
                // console.log(edges);
                for (const start in routes) {
                    visit(start,routes[start], 0, false, ""+start);
                }
            
                let count = 0;
                for (let i = 1; i < isGood.length; i++) {
                    if(isGood[i]) {
                        count++;
                    }   
                }
                console.log(count)
                
                for (let i = 1; i < isGood.length; i++) {
                    if(isGood[i]) {
                        console.log(i)
                    }   
                }
                readline.close();
            }
        } 
    })
    
    // for (let i = 0; i < n-1; i++) {
    //     let temp = (await read("")).split(" ");
    //     let start = parseInt(temp[0]), end = parseInt(temp[1]), weight = parseInt(temp[2]);
    //     edges[temp[0]+temp[1]] = weight;
    //     edges[temp[1]+temp[0]] = weight;
    //     if(!routes[start]) {
    //         routes[start] = [];
    //     }
    //     routes[start].push(end);
    //     if(!routes[end]) {
    //         routes[end] = [];
    //     }
    //     routes[end].push(start);
    // }
    // console.log(JSON.stringify(routes));
    // console.log((routes));

    // console.log(isGood);
    // console.log(edges);
    // for (const start in routes) {
    //     visit(start,routes[start], 0, false, ""+start);
    // }

    // let count = 0;
    // for (let i = 1; i < isGood.length; i++) {
    //     if(isGood[i]) {
    //         count++;
    //     }   
    // }
    // console.log(count)
    
    // for (let i = 1; i < isGood.length; i++) {
    //     if(isGood[i]) {
    //         console.log(i)
    //     }   
    // }
    
    
    // console.log(JSON.stringify(routes));
    // console.log(isGood);
    // console.log(edges);
    // readline.close()
    return;
}

run()