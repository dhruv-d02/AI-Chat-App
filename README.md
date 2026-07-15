# AI Chat App

AI Chat App is an Android project built as part of my roadmap to grow into an Android Engineer with a strong focus on AI integration.

This project starts with a simple Android foundation and will evolve step by step into a structured AI-powered chat application. Along the way, it will cover modern Android development, clean architecture, networking, state management, and practical AI API integration.

## Project Vision

The goal is not just to build a chat app, but to understand how production-ready Android applications can integrate AI features in a clean, scalable, and maintainable way.

This repository will document that journey through small, intentional milestones.

## Current Focus

- Kotlin-based Android development
- Jetpack Compose UI foundation
- Clean Architecture layering (`data`, `domain`, `presentation`, `di`)
- Version catalog based dependency management
- Direct OpenAI API integration via Retrofit, for learning purposes

## Milestones

### Chat UI foundation
- Replaced the default template screen with a chat-style UI: an empty-start message list and a bottom input bar.
- `ChatUiMessage` models a message as `id`, `text`, and `MessageSender` (`User` or `Assistant`).
- `MessageBubble` aligns user and assistant messages differently so a conversation reads like a real chat.
- `ChatScreen` delegates rendering to a stateless `ChatContent` composable, keeping the screen itself thin.

### State management with ViewModel
- Moved message state out of `ChatScreen` and into `ChatViewModel`, exposed as a `StateFlow<ChatUiState>`.
- `ChatUiState` carries `messages`, `isLoading`, and `errorMessage` so the UI can react to loading and error conditions, not just message content.
- `MessageInputBar` owns only its own temporary input text; it does not own conversation state.

### OpenAI Retrofit integration
- Added a direct OpenAI API call path using Retrofit, purely as a learning exercise. **This is not production-safe** — an API key bundled in `BuildConfig` can be extracted from an APK. A backend proxy is the intended production approach and is planned for a later milestone.
- `OpenAiApiService` targets the `/v1/responses` endpoint; `OpenAiResponseRequest` and `OpenAiResponseDto` model the request/response shape.
- `RetrofitServiceFactory` builds the shared Retrofit/OkHttp client. `BuildConfig.OPENAI_API_KEY` is sourced from the `OPENAI_API_KEY` environment variable at build time — it is never committed to source.
- Added the `INTERNET` permission required for any of this to work.
- Introduced a domain layer to keep networking details out of the ViewModel: `ChatResponseState` (`Loading` / `Success` / `Error`) is the contract exposed to the UI, `ChatRepository` defines the operation, `SendMessageUseCase` wraps it, and `ChatRepositoryImpl` implements it as a cold `Flow<ChatResponseState>` per request.
- The repository only reports results — it does not own the chat message list. `ChatViewModel` owns UI state, appends the user's message immediately on send, and appends an assistant message **only** when a real response comes back from the API. No fake or stubbed assistant replies are ever generated.
- A temporary manual DI object, `AppModule`, wires the service, repository, and use case together until a proper DI framework is introduced.

### Networking reliability fix
- Debugged a socket permission failure (resolved by confirming the `INTERNET` permission and reinstalling the app) and a subsequent ~10 second socket timeout on live requests.
- Fixed by giving the shared `OkHttpClient` explicit `connectTimeout`, `readTimeout`, and `writeTimeout` values, and by dropping `HttpLoggingInterceptor` from `BODY` to `BASIC` level so request/response bodies — including the API key and prompt text — are no longer written to Logcat.

## Roadmap Direction

Planned next:

- Conversation history sent with each request, not just the latest message
- Room-based local persistence for offline access to past conversations
- Backend proxy for the OpenAI API key instead of bundling it in the client
- Automated tests beyond the default project templates
- Richer error handling (distinguishing network, auth, and API errors) and possible response streaming

## Learning Approach

Every major addition to this project will be reflected in this README. The goal is to keep the repository useful not only as source code, but also as a visible record of the engineering decisions made throughout the roadmap.
