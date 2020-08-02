import 'package:flutter/material.dart';

class Question extends StatelessWidget {
  final String questionText;
  final String appMessage;

  Question(this.questionText, this.appMessage);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      margin: EdgeInsets.all(10),
      child: Text(appMessage, style: TextStyle(fontSize: 28),
      textAlign: TextAlign.center,),
    );
  }
}
