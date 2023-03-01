# MusicPlaylistApp

Test progect, which was develop using TDD cycle. I used such technologies as Kotlin, Coroutines, Flow, Retrofit, Jetpack Components(Lifecycle, ViewModel), Hilt, Havigation.
For testing app I had been using JUnit, Espresso and Mockito.

The application works with a local API, so for testing you need to run the server locally, using Mockoon for example, to return responses.

## Response example:
Get request to "[port]/playlists"
```gson5
[
  {
    "id": "1",
    "name": "Hard Rock Cafe",
    "category": "rock"
  },
    {
    "id": "2",
    "name": "Chilled House",
    "category": "house"
  },
    {
    "id": "3",
    "name": "US TOP 40 HITS",
    "category": "mixed"
  },
    {
    "id": "4",
    "name": "90's Rock",
    "category": "rock"
  },
    {
    "id": "5",
    "name": "Purple Jazz",
    "category": "jazz"
  },
  {
    "id": "6",
    "name": "90's flashback",
    "category": "pop"
  },
  {
    "id": "7",
    "name": "Machine Funk",
    "category": "electro"
  },
  {
    "id": "8",
    "name": "Let's Groove",
    "category": "mixed"
  },
  {
    "id": "9",
    "name": "Feel The Beat",
    "category": "electro"
  },
  {
    "id": "10",
    "name": "Best Songs 2020",
    "category": "mixed"
  }
 ]
```
Get request to "[port]/playlist-details/{id}"
```gson5
{
  "id": "1",
  "name": "Hard Rock Cafe",
  "details": "Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door"
}
```
