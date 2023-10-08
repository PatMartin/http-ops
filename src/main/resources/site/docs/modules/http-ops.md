# Http Ops

This module brings http based technologies to ops4j.

# Operations

## http:server

Design 1, using web-sockets.

```bash
op1 | web:socket 
```

```mermaid
flowchart LR
subgraph http:server
  ws:server
end
input-op --> http:server
http:server --> output-op
ws:server --> |web-socket events| browser
```

1. data flows into the http:server through execute(OpData input) and is appended to a List of ObjectNode.

2. The data is also sent to the internal ws:server which notifies any clients of data changes.

3. Browser apps can update with object constancy
   
   1. Flubber

## wss

```mermaid
flowchart LR

subgraph view
  ws[ws://localhost:4242/input]
  app1 & app2
end

input --> ws --> app1 & app2

app1 --> browser1
app2 --> browser2
view --> output
view --> browser1 & browser2
```


