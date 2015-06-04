TEIID_PATH=..\..\lib\*;..\..\optional\hbase\*;..\..\optional\jdbc\*;..\..\optional\tm\*;..\..\optional\*

javac -cp %TEIID_PATH% src\org\teiid\example\*.java 

java -cp %TEIID_PATH% org.teiid.example.TeiidEmbeddedHBaseDataSource %*

