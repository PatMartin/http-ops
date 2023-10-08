import { WebSocketServer } from 'ws';

const wss = new WebSocketServer({ host: "localhost", port: 8080, path: "/wss" });

wss.on('connection', function connection(ws) {
  ws.on('error', console.error);

  ws.on('message', function message(data) {
    console.log('received: %s', data);
  });

  ws.send('something');
});
