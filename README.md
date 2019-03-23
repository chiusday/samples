# samples
[vertx-reactive api](https://github.com/chiusday/samples/tree/master/vertx-reactive) returns stock prices based on the supplied ticker symbol. Internally, it will check if the price being queried is in it's database, if it is, then the prices are returned. Otherwise, it will call the [stocker](https://github.com/chiusday/samples/tree/master/stocker) API to pull the prices from sources (online/offline) which is transparent to vertx-reactive. Then save it in DB **asynchrously**, at the same time data is returned to the caller.

**Hibernate is not** used here because it doesn't have an asynchronous implementation yet, only synchronous data access. This goes against the intention of this project. This will (later on) use jooq for asynchronous data access.

## Overview:
![](vertx-reactive/src/main/resources/images/samplesDiagram.png)
