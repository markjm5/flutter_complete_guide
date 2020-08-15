import 'package:flutter/material.dart';

class Question extends StatelessWidget {
  final String questionText;
  final String appMessage;
  final String appCampaign;

  Question(this.questionText, this.appMessage, this.appCampaign);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      margin: EdgeInsets.all(10),
      child: Text(appCampaign.toString(), style: TextStyle(fontSize: 28),
      textAlign: TextAlign.center,),
    );
  }
}
