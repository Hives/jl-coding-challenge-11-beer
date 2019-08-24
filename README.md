# John Lewis Coding Challenge 11

Access the `pubcrawlapi.appspot.com` cache api, get all the pubs in the area,
and return a list of all the beers available at those pubs.

Example api output:
<https://pubcrawlapi.appspot.com/pubcache/?uId=mike&lng=-0.141499&lat=51.496466&deg=0.003>

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

Weirdly, IntelliJ seems to be telling me that my
`ObjectMapper().writeValueAsString()` invocation doesn't compile, and yet the
program will run. What's that about?

