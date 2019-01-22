# samples
### vertx-reactive
This is a reactive version of multiple-tables project.
RxJava is used to avoid nesting too many aynchronous handlers.
Visitor pattern is also used to show how it can greatly increase versatility

### springboot-angular:
This project shows how to build a single jar file that has both your backend spring boot services with angular front end. 
It downloads npm and node.js then builds your angular project. This is useful when you have to deploy the complete application (with angular UI) in a server that doesn’t have npm installed or internet access, which in my experience they almost always don’t

#### There are 2 things important here to make this work.
- [Maven Frontend Plugin](https://github.com/eirslett/frontend-maven-plugin) - Eirik Sletteberg’s Maven plugin that made all the angular build integration possible. Detailed explanation is provided by the spring boot guru Dsyer [here](https://github.com/dsyer/spring-boot-angular).
- Setting the build output path to the location where your UI components should be for the backend to effectively host them. In my case, I put them in /resources/static where spring boot automatically serves them as static resources.
For simplicity, I put my angular source files in a folder named "ui" and put it on the same level where my /resources folder is.
```xml
	-src
	  -main
	    -java
	    -resources
	    -ui
	      {angular source files goes here}
	      angular.json
	      src
	      node_modules
	      package.json
	      ...
```

then I update angular.json outputPath to ../resources/static
```xml
  "projects": {
    "no-npm": {
      ...
      "architect": {
        "build": {
          ...
          "options": {
            "outputPath": "../resources/static",

```

With this, just build your maven project like you normally do and it will install npm, build your angular UI and put them in your resource folder.
I hope this helps someone and hit me back if you have any comments or questions.


