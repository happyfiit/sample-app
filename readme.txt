
You will need to create a keystore.properties file and place this in the root folder of the project.
This file should contain information about accessing the keystore (name, password etc.) generated
when the keystore was first created.

keystore.properties:

storeFile=...<name of keystore>...
storePassword=...
keyAlias=...
keyPassword=...

A keystore named "my-release-key.keystore" has already been created and added to the repository. If
you do not have the keystore passwords you can always create and use your own with the command below:

${JAVA_HOME}/bin/keytool  -genkey -v -keystore my-release-key.keystore -alias alias_name -keyalg RSA -keysize 2048 -validity 10000

