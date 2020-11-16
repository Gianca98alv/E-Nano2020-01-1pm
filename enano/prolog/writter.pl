:- module(writter, [writeAST/2]).


writeAST(LL,N):-getFile(N,F),open(F,write,File),beginFile(N,File),writeList(LL,File),endFile(N,File),close(File).

beginFile(Name,File):-write(File,'import java.util.function.*; \n'),write(File,'import java.util.*; \n'),
write(File,'public class '), write(File,Name),write(File,' { \n').

endFile(Name,File):-write(File,'public static void main(String[] args){ \n'),write(File,'var me = new '),
write(File,Name),write(File,'(); \n'),write(File,' me.main();}}').

getFile(N,O):-atom_concat('clases\\',N,NN),atom_concat(NN,'.java',O).


writeList([],_).
writeList([H|T],File):-is_list(H),!,writeList(H,File),writeList(T,File).
writeList([H|T],File):-write(File,H),(member(H,['{','}',';'])->write(File,'\n');write(File,' ')),writeList(T,File).
