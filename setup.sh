#!/bin/zsh

mkdir -p ./spigot_build
cd ./spigot_build || { echo "ディレクトリの移動に失敗しました。"; exit 1; }

echo "BuildTools.jar のダウンロードを開始します..."
wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar
if [ $? -ne 0 ]; then
    echo "BuildTools.jar のダウンロードに失敗しました。"
    exit 1
fi
echo "BuildTools.jar のダウンロードが完了しました。"

echo "BuildTools.jar を実行して Spigot をビルドします..."
java -jar BuildTools.jar --rev latest
if [ $? -ne 0 ]; then
    echo "Spigot のビルドに失敗しました。"
    exit 1
fi
echo "Spigot のビルドが完了しました。"

echo "ビルドされた Spigot の JAR ファイルを確認しています..."
if [ -f spigot-*.jar ]; then
    echo "Spigot の JAR ファイルが正常にビルドされました。"
else
    echo "Spigot の JAR ファイルが見つかりません。ビルドに失敗した可能性があります。"
    exit 1
fi
