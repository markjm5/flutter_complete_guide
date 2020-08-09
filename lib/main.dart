import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import './quiz.dart';
import './result.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp> {

  static const platform = const MethodChannel('demo.flutter_complete_guide/info'); 
  String _message;

  @override
  void initState(){

    _getMessage().then((String message){
      setState(() {
        _message = message;        
      });   
   });

   super.initState();
   //print(_message);

  }


  final _questions = const [
    {
      'questionText': 'What\'s your favourite colour?',
      'answers': [
        {'text': 'Black', 'score': 10},
        {'text': 'Red', 'score': 5},
        {'text': 'Green', 'score': 3},
        {'text': 'White', 'score': 1},
      ]
    },
    {
      'questionText': 'What\'s your favourite animal?',
      'answers': [
        {'text': 'Rabbit', 'score': 3},
        {'text': 'Snake', 'score': 11},
        {'text': 'Elephant', 'score': 5},
        {'text': 'Lion', 'score': 9},
      ]
    },
    {
      'questionText': 'Who\'s your favourite instructor?',
      'answers': [
        {'text': 'Max', 'score': 1},
        {'text': 'Max', 'score': 1},
        {'text': 'Max', 'score': 1},
        {'text': 'Max', 'score': 1},
      ]
    },
  ];

  var _questionIndex = 0;
  var _totalScore = 0;

  void _resetQuiz(){

    setState((){
      _questionIndex = 0;
      _totalScore = 0;
      }
    );
  }

  void _answerQuestion(int score, String answerText, Function logEvent) {
    logEvent(answerText).then((String message){
      setState(() {
        _message = message;        
      });   
   });

    _totalScore += score;

    setState(() {
      _questionIndex = _questionIndex + 1;
    });
    print(_questionIndex);
    if (_questionIndex < _questions.length) {
      print('We have more questions!');
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('My Quiz App'),
        ),
        body: _questionIndex < _questions.length
            ? Quiz(
                answerQuestion: _answerQuestion,
                questionIndex: _questionIndex,
                questions: _questions,
                appMessage: _message,
                logEvent: _logEvent,
              )
            : Result(_totalScore, _resetQuiz),
      ),
    );
  }
  
  Future<String> _getMessage() async {
    var sendMap = <String, dynamic> {
      'account': 'interactionstudio',
      'ds': 'mmukherjee_sandbox',
    };

    String value;
    try {
      value = await platform.invokeMethod('getMessage', sendMap);
    } catch (e){
      print(e);
    }

    return value;
  }

  Future<String> _logEvent(String answerChosen) async {
    var sendMap = <String, dynamic> {
      'event': 'Answer Selected: ' + answerChosen,
    };

    String value;
    try {
      value = await platform.invokeMethod('logEvent', sendMap);
    } catch (e){
      print(e);
    }

    return value;
  }  

}
