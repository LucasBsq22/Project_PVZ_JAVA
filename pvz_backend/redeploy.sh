#!/bin/bash

# Variables
TOMCAT_WEBAPPS="/opt/homebrew/Cellar/tomcat/11.0.5/libexec/webapps"
WAR_NAME="CoursEpfBack.war"

echo "🔄 Déploiement automatique de $WAR_NAME sur Tomcat..."

# Étape 1 : Compilation du projet
echo "📦 Compilation du projet avec Maven..."
mvn clean package

if [ $? -ne 0 ]; then
  echo "❌ Échec de la compilation Maven."
  exit 1
fi

# Étape 2 : Suppression de l'ancien déploiement
echo "🧹 Suppression de l'ancien déploiement..."
rm -rf "$TOMCAT_WEBAPPS/CoursEpfBack"
rm -f "$TOMCAT_WEBAPPS/$WAR_NAME"

# Étape 3 : Copie du nouveau .war
echo "📂 Copie du nouveau fichier WAR..."
cp "target/$WAR_NAME" "$TOMCAT_WEBAPPS/"

# Étape 4 : Redémarrage de Tomcat
echo "♻️ Redémarrage de Tomcat..."
catalina stop
sleep 2
catalina start

echo "✅ Déploiement terminé !"
echo "🌍 Accède à : http://localhost:8080/CoursEpfBack"
