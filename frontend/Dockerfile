FROM node:20-alpine
WORKDIR ./app
COPY package*.json ./
RUN npm install
COPY . .
ARG NG_APP_API_URL
ENV NG_APP_API_URL=$NG_APP_API_URL
CMD ["npm", "run", "start", "--", "--host", "0.0.0.0"]