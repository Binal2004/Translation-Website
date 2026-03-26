# 🌐 Google API Translator App

A Java web application that translates text and converts it to speech using **Google Cloud Translation** and **Google Cloud Text-to-Speech** APIs. Built with Jakarta EE 10 and deployed as a WAR on a servlet container.

---

## ✨ Features

- **Text Translation** — Translate input text into 10 supported languages
- **Text-to-Speech** — Convert input text directly to audio (WAV)
- **Translated Speech** — Translate text and immediately play the translation as audio
- **Copy to Clipboard** — One-click copy of translated output
- Animated gradient UI with fade-in effects

### Supported Languages
French, Spanish, German, Hindi, Italian, Japanese, Chinese, Russian, Arabic, Portuguese

---

## 🗂️ Project Structure

```
Java-Translation-Project/
│
├── src/main/
│   ├── java/
│   │   ├── TranslationCode.java                        # Core servlet (translate + TTS logic)
│   │   └── com/mycompany/texttospeechp/
│   │       ├── JakartaRestConfiguration.java           # Jakarta REST config
│   │       └── resources/JakartaEE10Resource.java      # REST resource
│   │
│   ├── resources/
│   │   └── META-INF/persistence.xml                   # JPA persistence config
│   │
│   └── webapp/
│       ├── index.jsp                                   # Main UI (input form)
│       ├── result.jsp                                  # Translation result page
│       ├── playAudio.jsp                               # Audio playback page
│       ├── META-INF/context.xml
│       └── WEB-INF/
│           ├── web.xml
│           └── beans.xml
│
└── pom.xml                                             # Maven build config
```

---

## ⚙️ How It Works

The `TranslationCode` servlet handles three actions via a single POST form:

| Action | What happens |
|---|---|
| `translate` | Calls Google Translate API → displays result on `result.jsp` |
| `textToSpeech` | Calls Google TTS API on original input → plays audio on `playAudio.jsp` |
| `textToSpeechTranslation` | Translates first, then converts translation to audio → plays on `playAudio.jsp` |

Audio is returned as raw WAV bytes, Base64-encoded, and embedded directly in the browser's `<audio>` element — no file storage needed.

TTS voice used: **`en-US-Wavenet-D`** (LINEAR16 encoding)

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Web Framework | Jakarta EE 10 (Servlets + JSP) |
| Build Tool | Maven |
| Translation API | Google Cloud Translation v3 |
| Text-to-Speech API | Google Cloud Text-to-Speech v1 |
| Auth | Google Auth Library (OAuth2) |
| Packaging | WAR |

---

## 🚀 Getting Started

### Prerequisites
- Java 11+
- Maven 3.6+
- A servlet container (Apache Tomcat 10+ recommended for Jakarta EE 10)
- A **Google Cloud project** with the following APIs enabled:
  - Cloud Translation API
  - Cloud Text-to-Speech API
- A Google Cloud **service account key** (JSON credentials file)

### 1. Set Up Google Cloud Credentials

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a service account and download the JSON key file
3. In `TranslationCode.java`, replace the placeholder with your credentials path and project ID:

```java
String credentialsPath = "path/to/your/credentials.json";
// ...
.setParent("projects/YOUR_PROJECT_ID")
```

### 2. Build the Project

```bash
mvn clean package
```

This generates a `.war` file in the `target/` directory.

### 3. Deploy

Copy the WAR to your Tomcat `webapps/` directory:

```bash
cp target/JavaTranslationProject-1.0-SNAPSHOT.war /path/to/tomcat/webapps/
```

Then start Tomcat and navigate to:

```
http://localhost:8080/JavaTranslationProject-1.0-SNAPSHOT/
```

---

## 📦 Key Dependencies (pom.xml)

| Dependency | Version |
|---|---|
| jakarta.jakartaee-api | 10.0.0 |
| google-cloud-translate | 2.47.0 |
| google-cloud-texttospeech | 2.48.0 |
| google-cloud-core | 2.42.0 |
| google-auth-library-oauth2-http | 1.24.0 |
| gax | 2.51.0 |
| guava | 33.3.0-jre |

---

## 🔮 Potential Improvements

- [ ] Auto-detect source language
- [ ] Support more target languages
- [ ] Allow user to select TTS voice/language for output audio
- [ ] Save translation history
- [ ] Add support for document/file translation
