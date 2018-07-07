var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var twoPlayerWaiting = [];
var twoPlayerRooms = [];

server.listen(8081, function(){
	console.log("Server is now running...");
});

io.on('connection', function(socket){
	console.log("Player connected...");
	players.push(new player(socket.id));
	socket.emit('socketID',{ id: socket.id });
	
	console.log("2 players room");
	for(var i=0; i < players.length; i++){
		if(players[i].id == socket.id){
			players.splice(i, 1);
		}
	}
	if(twoPlayerWaiting.length > 0){
		twoPlayerRooms.push(new room2(socket.id, twoPlayerWaiting[0].id));
		socket.emit('startGameB', {});
		io.to(twoPlayerWaiting[0].id).emit('startGameW', {});
		twoPlayerWaiting.splice(0, 1);
	}
	else{
		twoPlayerWaiting.push(new player(socket.id));
	}
	
	socket.on('moved', function(data){
		console.log("move made");
		for(var i=0; i< twoPlayerRooms.length; i++){
			if(twoPlayerRooms[i].id1 == socket.id){
				io.to(twoPlayerRooms[i].id2).emit('moved', data);
			}
			else if(twoPlayerRooms[i].id2 == socket.id){
				io.to(twoPlayerRooms[i].id1).emit('moved', data);
			}
		}
	});
	
	socket.on('win', function(){
		console.log("match over");
		for(var i=0; i< twoPlayerRooms.length; i++){
			if(twoPlayerRooms[i].id1 == socket.id){
				io.to(twoPlayerRooms[i].id2).emit('lose', {});
				twoPlayerRooms.splice(i, 1);
			}
			else if(twoPlayerRooms[i].id2 == socket.id){
				io.to(twoPlayerRooms[i].id1).emit('lose', {});
				twoPlayerRooms.splice(i, 1);
			}
		}
	});
	socket.on('disconnect', function(){
		console.log("Player disconnected!");
		for(var i=0; i < players.length; i++){
			if(players[i].id == socket.id){
				players.splice(i, 1);
			}
		}
		for(var i=0; i< twoPlayerRooms.length; i++){
			if(twoPlayerRooms[i].id1 == socket.id){
				io.to(twoPlayerRooms[i].id2).emit('win', {});
				twoPlayerRooms.splice(i, 1);
			}
			else if(twoPlayerRooms[i].id2 == socket.id){
				io.to(twoPlayerRooms[i].id1).emit('win', {});
				twoPlayerRooms.splice(i, 1);
			}
		}
		for(var i=0; i< twoPlayerWaiting.length; i++){
			if(twoPlayerWaiting[i].id == socket.id){
				twoPlayerWaiting.splice(i, 1);
			}
		}
	});
});

function player(id){
	this.id = id;
}

function room2(id1, id2){
	this.id1 = id1;
	this.id2 = id2;
}