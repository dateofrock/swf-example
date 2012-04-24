swf-example
===================
Amazon Simple Workflow Serviceのサンプルプログラムです。[ブログ](http://blog.dateofrock.com/2012/03/amazon-swf.html)に解説があります。

Usage
----
* [src/AwsCredentials.properties](https://github.com/dateofrock/swf-example/blob/master/src/AwsCredentials.properties.txt)にAWSのアクセスキー、シークレットキーを入れてください。
* メール通知には[Amazon Simple Notification Service](http://aws.amazon.com/jp/sns/)を使用します。あらかじめトピックを作成して、APIエンドポイントとARNを[src/config.properties](https://github.com/dateofrock/swf-example/blob/master/src/config.properties.txt)に書き込んでください。
* 実行手順は、最初に[ActivityHost](https://github.com/dateofrock/swf-example/blob/master/src/com/dateofrock/example/aws/swf/worker/ActivityHost.java)、[WorkflowHost](https://github.com/dateofrock/swf-example/blob/master/src/com/dateofrock/example/aws/swf/worker/WorkflowHost.java)を起動してください。その後、[Starter](https://github.com/dateofrock/swf-example/blob/master/src/com/dateofrock/example/Starter.java)を起動すると、ワークフローが実行されます。


Licence
----
* ソースコードはApache Licence 2.0とし、すべてをgithub上に公開する事とします。
* 当ソフトウェアの動作は保証しません。ご自身の責任と判断でご利用ください。
* 当ソフトウェアを利用することにより発生したいかなる損害も当方は責任を負わないものとします。


Author
==============

Takehito Tanabe - [dateofrock](http://blog.dateofrock.com/)