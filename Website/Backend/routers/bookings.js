var express = require('express');
var router = express.Router();
var mqttClient = require('./../mqttHelper.js');
 
router.post('/bookings/:id', (req, res) => {
  console.log("create booking for patient by id")
  mqttClient.handleRequest(req, res, 'toothtrek/booking_service/booking/create/', req.params.id, req.body);
});
//get bookings for patient by id
router.get('/bookings/:id', (req, res) => {
  mqttClient.handleRequest(req, res, 'toothtrek/booking_service/booking/get/', req.params.id, req.body);
});

router.delete('/bookings/:patentId/:bookingId', (req, res) => {
  var publishJson = {
    "patientId": req.params.patentId,
    "id": req.params.bookingId,
    state: "cancelled"
  }
  
  console.log("got reques twith user id: " + req.params.patentId + " and booking id: " + req.params.bookingId)
  mqttClient.handleRequest(req, res, 'toothtrek/booking_service/booking/state', req.params.patentId, publishJson);
});

module.exports = router;