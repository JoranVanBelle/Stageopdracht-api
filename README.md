# Stageopdracht - API

Deze repository maakt deel uit van mijn stageopdracht. Tijdens mijn stageopdracht moest er een event-driven architectuur (EDA) uitgevoerd worden. Andere repo's van mijn opdracht zijn [de adapter](https://github.com/JoranVanBelle/Stageopdracht) en [de UI](https://github.com/JoranVanBelle/Stageopdracht-UI).

# Inhoud

De code voorziet ook documentatie via open-API die terug te vinden is onder ``localhost:5000/api-docs`` en documentatie via swagger ``localhost:5000/swagger-api-docs.html``.
Verder zijn er ook een ``Dockerfile`` en ``docker-compose.yml`` voorzien om alles in docker te testen.

# Starten

Om het project in docker te runnen moeten volgende stappen uitgevoerd worden in de rootfolder van het project:
  1. Project builden
    1.1 Windows: ``.\mvnw clean install``
    1.2 Linux: ``\mvnw clean install``
  
  2. Docker image maken: ``docker build -t api:latest .``

  3. Docker containers runnen: ``docker-compose up``

# Env variables

  Dit project vereist ook enkele variabele (om het in google cloud te runnen):

  - PLAIN_LOGIN_MODULE_PASSWORD
  - PLAIN_LOGIN_MODULE_USERNAME
  - BASIC_AUTH_USER_INFO
  - DATABASE_USERNAME
  - DATABASE_PASSWORD
  - DATABASE_INSTANCE_CONNECTION_NAME
  - DATABASE_NAME
  - BOOTSTRAP_SERVERS
  - CLUSTER_API_KEY
  - CLUSTER_API_SECRET
  - SR_API_KEY
  - SR_API_SECRET
  - SCHEMA_REGISTRY_URL
  - EMAIL_HOST
  - EMAIL_PORT
  - EMAIL_USERNAME
  - EMAIL_PASSWORD
  - DISTANCE_MATRIX_API_TOKEN
  - PTV_API_TOKEN