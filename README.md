# John Lewis Coding Challenge 11

Access the `pubcrawlapi.appspot.com/pubcache` api, get all the pubs in the area,
and return a list of all the beers available at those pubs.

Example api output:
<https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=-0.141499&lat=51.496466&deg=0.003>

[Full challenge details](https://coding-challenges.jl-engineering.net/challenges/challenge-11/)

## Using

- Kotlin
- ~~Ktor - web framework~~
- Jackson - JSON for Java

## Running

To run, execute:

```shell script
$ ./gradlew run
```

Program will print JSON output to the terminal.

## Tests

No tests.

## Thoughts, etc.

Functional approach again. Spiked it to figure out how ~~Ktor and~~ Jackson works. I'm planning to TDD it using HTTP4K, making a little API that you can `curl` or access in the browser. Could also make a front end to allow people to chose a latitude/longitude and range.

I started off using Ktor to make an HTTP request to the API, but it turns out Jackson can retrieve JSON directly given a URL, so I removed the Ktor dependency and did that instead.
