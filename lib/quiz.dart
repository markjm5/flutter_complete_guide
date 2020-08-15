import 'package:flutter/material.dart';
import './question.dart';
import './answer.dart';
import './messaging.dart';

class Quiz extends StatelessWidget {
  final List<Map<String, Object>> questions;
  final int questionIndex;
  final Function answerQuestion;
  final String appMessage;
  final String appCampaign;
  final Function androidLogEvent;

  Quiz({@required this.questions, @required this.answerQuestion, @required this.questionIndex, @required this.appMessage, @required this.appCampaign, @required this.androidLogEvent});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        MessagingWidget(),
        Question(
          questions[questionIndex]['questionText'],
          appMessage,
          appCampaign,
        ),
        ...(questions[questionIndex]['answers'] as List<Map<String, Object>>).map((answer) {
          return Answer(() => answerQuestion(answer['score'], answer['text'], androidLogEvent), answer['text']);
        }).toList()
      ],
    );
  }
}
