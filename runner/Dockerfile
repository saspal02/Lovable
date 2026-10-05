FROM node:22-alpine

WORKDIR /app

COPY package.json /opt/prebake/package.json

RUN cd /opt/prebake && npm install --no-fund --no-audit

CMD ["/bin/sh", "-c", "sleep infinity"]
