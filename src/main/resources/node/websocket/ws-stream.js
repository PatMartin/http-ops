import WebSocket, { createWebSocketStream } from 'ws';

const ws = new WebSocket('ws://localhost:8080/wss');

const duplex = createWebSocketStream(ws, { encoding: 'utf8' });

duplex.on('error', console.error);

duplex.pipe(process.stdout);
process.stdin.pipe(duplex);
