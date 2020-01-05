var functions = require('firebase-functions');
var admin = require('firebase-admin');

admin.initializeApp(functions.config().firebase);

exports.room1Password = functions.database.ref('USER-BOOKING/{users}/room1/{id_booking}')
        .onWrite((change, context) => {
  		
  		var eventSnapshot = change.after;
          
        //Memanggil Value Child  
        var password = eventSnapshot.child("password").val();
        var tanggal = eventSnapshot.child("tanggal").val();
        var start = eventSnapshot.child("start").val();
        var end = eventSnapshot.child("end").val();
        var id_booking = eventSnapshot.child("id_booking").val();
        var userid = eventSnapshot.child("userid").val();
        var status = eventSnapshot.child("status").val();
        
        var gmt = "GMT+07:00";
  		//Waktu Mulai
        var before = tanggal+" "+start+" "+gmt;
        var timeBefore= new Date(before);
  		var dateBefore = timeBefore.getTime();
  		//Waktu Selesai
  		var after = tanggal+" "+end+" "+gmt;
        var timeAfter = new Date(after);
        var dateAfter = timeAfter.getTime();

        var waktuRef = admin.database().ref('Waktu/now');
        waktuRef.on('value', function(snapshot){
            //Waktu Sekarang
            var now = snapshot.val();

              if(dateBefore < now && now < dateAfter && status=="0"){
                return admin.database().ref('GEDUNG/room1').update({
                    password : password
                })
              } else if(now > dateAfter){
                var userStatus = admin.database().ref('USER-BOOKING/'+userid+'/room1/'+id_booking).update({
                        status : "1"
                    });
                var allStatus = admin.database().ref('BOOKING/room1/'+id_booking).update({
                       status : "1"
                    });
                return {userStatus, allStatus};
                } 
            })
        }); 

exports.room2Password = functions.database.ref('USER-BOOKING/{users}/room2/{id_booking}')
        .onWrite((change, context) => {
  		
  		var eventSnapshot = change.after;
          
        //Memanggil Value Child  
        var password = eventSnapshot.child("password").val();
        var tanggal = eventSnapshot.child("tanggal").val();
        var start = eventSnapshot.child("start").val();
        var end = eventSnapshot.child("end").val();
        var id_booking = eventSnapshot.child("id_booking").val();
        var userid = eventSnapshot.child("userid").val();
        var status = eventSnapshot.child("status").val();
        
        var gmt = "GMT+07:00";
  		//Waktu Mulai
        var before = tanggal+" "+start+" "+gmt;
        var timeBefore= new Date(before);
  		var dateBefore = timeBefore.getTime();
  		//Waktu Selesai
  		var after = tanggal+" "+end+" "+gmt;
        var timeAfter = new Date(after);
        var dateAfter = timeAfter.getTime();

        var waktuRef = admin.database().ref('Waktu/now');
        waktuRef.on('value', function(snapshot){
            //Waktu Sekarang
            var now = snapshot.val();

              if(dateBefore < now && now < dateAfter && status=="0"){
                return admin.database().ref('GEDUNG/room2').update({
                    password : password
                })
              } else if(now > dateAfter){
                var userStatus = admin.database().ref('USER-BOOKING/'+userid+'/room2/'+id_booking).update({
                        status : "1"
                    });
                var allStatus = admin.database().ref('BOOKING/room2/'+id_booking).update({
                       status : "1"
                    });
                return {userStatus, allStatus};
                } 
            })
        }); 