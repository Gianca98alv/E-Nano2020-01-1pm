:- module(parser, [transpile/2]).

:- use_module(library(pcre)).
:-use_module(lexer,[tokenize_file/2 as tokenize]).
:-use_module(writter,[writeAST/2 as writeT]).

:-dynamic imports/1.

/*
:-retractall(imports(_)),assert(imports([sd])).
myMem(I,LL):-member(I,LL),!.
isMember(Imp):-retract(imports(I)),myMem(Imp,I)->assert(imports(I));assert(imports([Imp|I])).
%addImport(Imp):-retract(imports(I)),.*/


transpile(File,Name):-tokenize(File,Tokens),(parseNano(A,Tokens,[]))->(!,writeT(A,Name));throw('No se ha podido transpilar').

parseNano([Dec|Main])-->declarations(Dec),main(Main).
declarations([D|Rest])-->declaration(D),declarations(Rest).
declarations([])-->[].
declaration([final,Tipo,Id,=,Val,';'])-->keyword,[<],varType(Tipo),[>],id(Id),[=],value(Val).
declaration([final,Tipo,Id,';'])-->keyword,[<],varType(Tipo),[>],id(Id).
declaration(TM)-->typeMap(TM).
declaration(M)-->[method],method(M).
declaration(['final List<',TC,'>',Id,'=',ListBody])--> keyword,[<],['['],[T],{compType(T,TC)},[']'],[>],id(Id),[=],listBody(ListBody).

listBody(['Arrays.asList','(',LI,')',';'])-->['['],listItems(LI),[']'].
listItems([H|T])-->item(H),tail(T).
tail([',',I|R])-->[','],item(I),tail(R).
tail([])-->[].
item(I)-->id(I).
item(I)-->value(I).
item(I)-->operation(I).
item([-,I])-->[-],operation(I).
item(['-',I])-->[-],id(I).
item(I)-->methodCall(I).

typeMap([final,'UnaryOperator','<',Type,'>',Id,'=',UB])-->keyword,[<],[Ta],[->],[Ta],[>],id(Id),[=],unaryOpBody(UB),
{compType(Ta,Type)}.

typeMap([final,'Function','<',T1,',',T2,'>',Id,'=',FB])-->keyword,[<],[Ta],[->],[Tb],[>],id(Id),[=],functionBody(FB),
{compType(Ta,T1),compType(Tb,T2)}.

typeMap([final,'BiFunction','<',T1,',',T2,',',T3,'>',Id,'=',BFB])-->keyword,[<],[Ta],[','],[Tb],[->],[Tc],[>],id(Id),[=],biFunctionBody(BFB),
{compType(Ta,T1),compType(Tb,T2),compType(Tc,T3)}.


main(['public void main()','{',Body,'}'])-->[main],['{'],mainBody(Body),['}'].
mainBody([])-->[].
mainBody([L|Rest])-->mainLine(L),mainBody(Rest).
mainLine(L)-->declaration(L).
mainLine(L)-->simplePrint(L).
mainLine(L)-->formatPrint(L).

simplePrint(['System.out.println','(',I,')',';'])-->[println],['('],item(I),[')'].

formatPrint(['System.out.println(String.format(',Format,',',LI,')',')',';'])-->[println],['('],['String'],['.'],['format'],
['('],[Format],[','],listItems(LI),[')'],[')'].


unaryOpBody([x,'->',A])-->if_else(A).
unaryOpBody([x,'->',A])-->uAsignation(A).

functionBody([x,'->',A])-->if_else(A).
functionBody([x,'->',A])-->fAsignation(A).

biFunctionBody(['(',Id1,',',Id2,')','->',A])-->[Id1],[','],[Id2],bfAsignation(A).

method([Out,Id,'(',In,Par,')',B])-->['<'],varType(In),['->'],validType(Out),['>'], id(Id),['('],id(Par),[')'],['='],methodBody(B).
methodBody(['{',return,B,'}'])-->if_else(B).


%Lambda Asignation
uAsignation(Id)-->id(Id),['->'],id(Id).
uAsignation([-,Id])-->[-],id(Id).
uAsignation([Id,'=',Id2,';'])-->id(Id),['->'],id(Id2).
uAsignation([Id,'=',Val,';'])-->id(Id),['->'],value(Val).

fAsignation(Op)-->operation(Op).
fAsignation([Id,'=',Val,';'])-->id(Id),['->'],operation(Val).

bfAsignation([Val,';'])-->[->],operation(Val).


%Method asignation
asignation(Val)-->value(Val).
asignation(Op)-->operation(Op).
asignation(Id)-->id(Id),['->'],id(Id).
asignation([-,Id])-->[-],id(Id).
asignation([Id,'=',Id2,';'])-->id(Id),['->'],id(Id2).
asignation([Id,'=',Val,';'])-->id(Id),['->'],value(Val).


operation([F1,Op,F2])-->factor(F1),operator(Op),factor(F2).
factor(F)-->methodCall(F).
factor(F)-->value(F).
factor(F)-->id(F).

methodCall([Id,['('],Par,[')']])-->id(Id),['('],operation(Par),[')'].
methodCall([Id,['('],Par,[')']])-->id(Id),['('],listItems(Par),[')'].
methodCall([Id,['.'],MC])-->id(Id),['.'],methodCall(MC).


if_else(['(',Comp,')','?',A1,':',A2,';'])-->asignation(A1),[if], comparison(Comp),[else], asignation(A2).


comparison([Id,S,Num])-->id(Id),compSymbol(S),value(Num).
comparison([Id,S,Id2])-->id(Id),compSymbol(S),id(Id2).



%varTypes(R)-->,{validType(Ta),validType(Tb),compType(Ta,R),compType(Tb,R)}.
id(Id)-->[Id],{not(number(Id)),re_match("[\\w]*",Id)}.
value(Val)-->[Val],{number(Val),!}.
varType(Type)-->[Type],{validType(Type),!}.
keyword-->[K],{member(K,[var,val]),!}.
validType('String').
validType('double').
validType('int').
validType(T)-->[T],{validType(T)}.
compType('int','Integer').
compType('double','Double').
compType('bool','Boolean').
getComType(T)-->[Type],{validType(Type),compType(Type,T)}.
compSymbol(S)-->[S],{member(S,['==','<=','>=','<','>']),!}.
operator(X)-->[X],{member(X,[*,'/','+','-']),!}.




