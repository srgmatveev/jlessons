#java -jar proguard\proguard-base-5.3.3.jar @work01.pro

-injars       target/L01.1-maven.jar
-outjars      target/L01.2.1-release.jar

-libraryjars  <java.home>/lib/rt.jar #contains all of the compiled class files for the base Java Runtime environment
-printmapping pgmapout.map
-dontwarn

-keep public class ru.hw01.work01.Main {public static void main(java.lang.String[]);}