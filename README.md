# TFT Friend Ladder

A **Java application** that connects to the Riot Games API to analyze **Teamfight Tactics (TFT)** player performance. It fetches match history, processes detailed game statistics, and calculates progress over time for different queue types.

---

## 🔑 Requirements

### Riot Developer API Key

To use the application, you must have a **personal Riot Games Developer API key**, which you can obtain from:

> [https://developer.riotgames.com/](https://developer.riotgames.com/)

Your key is valid for 24 hours and must be refreshed regularly during development.

---

## 🧩 Features

* Fetches full TFT match history for a given player.
* Supports **pagination** and **rate-limit handling** for Riot’s API.
* Separates and stores matches by **queue type**
* Filters matches by **set number** (e.g., Set 15).
* Calculates and displays player statistics such as:

  * Winrate
  * Average placement
  * Average total damage
  * Average players eliminated
  * Favorite traits and units
* Includes a **progress tracker** that compares recent performance (e.g., last 20 games) to overall stats.

---

## ⚙️ How It Works

1. The application uses Riot’s **TFT Match-V1** and **TFT Summoner-V1** APIs.
2. It starts by fetching a player’s PUUID and match history
3. For each match ID, it retrieves detailed match data
4. Statistics are computed, and progress is printed or saved.

---

## 🌱 Environment Setup

You should store your API key securely in an **environment variable** rather than hardcoding it.

### In an IDE

You can set up the environment variable directly in your IDE - usually under run configuration.

### Example (Windows PowerShell)

```powershell
setx RIOT_API_KEY "RGAPI-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
```

### Example (Linux / macOS)

```bash
export RIOT_API_KEY="RGAPI-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx"
```

In your code, you can access it like:

```java
String apiKey = System.getenv("RIOT_API_KEY");
```
---

## 🛡️ Rate Limiting

The Riot API enforces strict limits:

* **20 requests per second**
* **100 requests per 2 minutes**

The application automatically pauses when the limit is reached

---

## 🧠 Notes

* The program skips games with missing or invalid data (`null` info).
* If you encounter empty API responses, the code waits briefly and retries.
* The application stops fetching once it reaches older sets or the specified game count.

---

## 📁 Project Structure

```
src/
├── API_Calls.java           # Handles all Riot API requests
├── SummonerStats.java       # Stores and processes summoner statistics
├── TFT_Match.java           # Represents an individual match
└── Main.java                # Entry point for running the tracker
```

---

## 📜 License

This project is for **educational and personal use only**.
Riot Games owns all rights to the underlying Teamfight Tactics data and API.

---

## 🧑‍💻 Author

Developed by Ivor Sečkar
Built for exploring and analyzing TFT player performance data.
