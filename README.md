# samples
## vertx-reactive
This is a reactive version of multiple-tables project.
An API the returns stock prices based on the supplied ticker symbol. Internally, it will check if the price being queried is in it's database, if it is, then the prices are returned. Otherwise, it will call the [stocker](https://github.com/chiusday/samples/tree/master/stocker) API to pull the prices from sources (online/offline) that is transparent to vertx-reactive.

### Overview:
![](samples/blob/master/vertx-reactive/src/main/resources/images/samplesDiagram.png)

