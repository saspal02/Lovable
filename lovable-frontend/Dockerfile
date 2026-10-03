# syntax=docker/dockerfile:1

ARG NODE_VERSION=20-alpine
ARG NGINX_VERSION=alpine

# ---------- Stage 1: compile the SPA ----------
FROM node:${NODE_VERSION} AS build

WORKDIR /app

# Vite inlines VITE_* at build time, so this must be set before `npm run build`.
ARG VITE_API_URL=http://api.lovable.in
ENV VITE_API_URL=${VITE_API_URL}

# There is no committed lockfile, so `npm ci` is unavailable here.
COPY package.json ./
RUN --mount=type=cache,target=/root/.npm \
    npm install --no-fund --no-audit && npm cache clean --force

COPY . .

RUN npm run build

# ---------- Stage 2: serve the static output ----------
FROM nginx:${NGINX_VERSION} AS runtime

LABEL org.opencontainers.image.title="lovable-frontend" \
      org.opencontainers.image.description="Lovable web client: AI pair programming workspace" \
      org.opencontainers.image.source="https://github.com/saspal02/vibe-coding-platform" \
      org.opencontainers.image.licenses="UNLICENSED"

RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://127.0.0.1/healthz || exit 1

STOPSIGNAL SIGQUIT

CMD ["nginx", "-g", "daemon off;"]
