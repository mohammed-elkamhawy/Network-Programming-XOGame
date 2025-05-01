# ✍ Authors

Mohammed ElKamhawy

Ahmed Ashraf

Mohamed Hezema

Omar Ashba

---

# 🎮 XO Multiplayer Game (Tic Tac Toe) ❌⭕

A Java-based **XO (Tic Tac Toe)** game featuring both **Single Player** and **Multiplayer** modes, using **Sockets** for real-time client-server communication.

---

## 🧠 Architecture

This project follows the **MVVM (Model-View-ViewModel)** pattern within the principles of **Clean Architecture**, promoting separation of concerns, testability, and scalability.

### 🔷 MVVM Components

- **Model**: Handles the core game logic — board state, turn management, win detection, and game rules.
- **View**: Java Swing-based GUI (`MainMenuView`, etc.) for rendering the game board and handling user interactions.
- **ViewModel**: Acts as a bridge between the Model and the View — processes user input, updates the Model, and notifies the View of changes.

---

## ✅ Features

- 🔁 **Multiplayer Mode**: Play against another player over a local network
- 🤖 **Single Player Mode**: Play offline against the computer
- 🔄 **Game Reset**: Restart the game after each round
- 🖥️ **Clean GUI**: Built with Java Swing for a smooth user experience
- 🧱 **MVVM Architecture**: Organized, testable, and scalable codebase
- 🌐 **Socket Networking**: Real-time communication between client and server

---

## ✅ Clean Architecture Principles

- **Single Responsibility Principle**: Each class has a clearly defined role.
- **Separation of Concerns**: Logic, UI, and networking are fully decoupled.
- **Modular Design**: Easy to extend, test, and debug.

---

## 🛠 Requirements

- Java JDK 8 or higher
- Java IDE (e.g., NetBeans, IntelliJ IDEA, Eclipse)

---

## ▶️ How to Run

### 1. Start the Server

Compile and run the server:

```bash
javac GameServer.java
java GameServer
```
