#!/bin/sh

# Define the backup file name
BACKUP_FILE="backup.sql"

# Run the pg_dump command inside the db container
docker-compose exec db pg_dump --username="$POSTGRES_USER" --dbname="$POSTGRES_DB" --data-only --inserts > $BACKUP_FILE

echo "Backup completed: $BACKUP_FILE"