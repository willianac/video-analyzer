<h1 align="center">
Stanced
</h1>
<p align="center">
Gerador de resumos de vídeos do YouTube
</p>

## 🛠️ Criado com:

### Angular 20
### Java 25
### Spring
### MySQL
### Gemini API
### Google Cloud API
### Docker

## Para reproduzir o app na sua máquina, siga esses passos:

Obs: é necessário obter credenciais para utilizar tanto a API do Gemini quanto a do Google Cloud.

- Faça o clone desse repositório
- Crie um arquivo ".env" na raiz do projeto e adicione a credencial do Gemini (GEMINI_API_KEY={SUA_KEY})
- Dentro da pasta "credentials", adicione o arquivo json que é usado como credencial pelo Google Cloud API
- Abra o arquivo "docker-compose.yml", e no serviço "api", preencha a opção "GOOGLE_APPLICATION_CREDENTIALS" com o verdadeiro nome do arquivo json, exemplo: gen-lang-client-8490156840-f41c0912824b.json
- Rode no terminal: "docker compose up"