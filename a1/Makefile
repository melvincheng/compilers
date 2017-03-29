.PHONY: all run clean
JAVA = java
JAVAC = javac
SOURCE = MyLexer.java

CLASS = $(SOURCE:.java=.class)

all: $(CLASS)

run:
	$(JAVA) MyLexer token.txt testCase3.java

clean:
	rm *.class

%.class: %.java
	$(JAVAC) $<