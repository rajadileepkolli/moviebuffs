package moviebuffs

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class MovieBuffsAPISimulation extends Simulation {

  val httpConf = http
    .baseUrl("http://localhost:18080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  // Now, we can write the scenario as a composition
  val scnBrowseProducts = scenario("Browse Products")
    .during(30 minutes, "Counter") {
      exec(Browse.products).pause(2)
    }

  setUp(
      scnBrowseProducts.inject(rampUsers(500) during  (10 seconds))
      //,scnBrowseProducts.inject(atOnceUsers(100))
  ).protocols(httpConf)
    .assertions(
      global.responseTime.max.lt(800),
      global.successfulRequests.percent.gt(95)
    )

}

object Browse {
  val searchFeeder = csv("data/search.csv").random
  val genreFeeder = csv("data/genres.csv").random

  val searchInGenre =
    feed(genreFeeder)
    .feed(searchFeeder)
    .exec(http("Search Products By Genre")
    .get("/api/movies?genre=${genre}&query=${key}"))
    .pause(3)

  val byGenre = feed(genreFeeder)
    .exec(http("Products By Genre")
      .get("/api/movies?genre=${genre}"))
    .pause(3)

  val search = feed(searchFeeder)
    .exec(http("Search")
      .get("/api/movies?query=${key}"))
    .pause(3)

  val gotoPage = repeat(5, "n") {
    exec{ session => session.set("pageNo", session("n").as[Int] + 1) }
    .exec(http("Products Page ${pageNo}").get("/api/movies?page=${pageNo}"))
    .pause(1)
  }

  val products =
    exec(gotoPage)
      .exec(search)
      .exec(byGenre)
      .exec(searchInGenre)

}
