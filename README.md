# John Lewis Coding Challenge 11

Access the `pubcrawlapi.appspot.com` cache api, get all the pubs in the area,
and return a list of all the beers available at those pubs.

[Full details](https://coding-challenges.jl-engineering.net/challenges/challenge-11/)

## Using

- Kotlin
- Ktor - web framework
- Jackson - JSON for Java

## Thoughts, etc.

Functional approach again. Spiked it just to figger out how Ktor and Jackson
work. Would be good to rewrite it using HTTP4K - could make a little API that
you can `curl` or view in the browser. Could even make a front end to allow
people to chose a latitude/longitude and range.
