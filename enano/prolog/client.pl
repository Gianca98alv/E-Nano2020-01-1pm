
%client
:- module(client, [runClass/2,compileFile/2, readTranspileFile/2]).

:- use_module(library(http/http_client)).

runClass(Reply, Body):- http_post('http://localhost:9000/run', atom('application/json', Body), Reply,[method(post)]).
compileFile(File, Reply):-http_post('http://localhost:9000/compile', atom('application/json', File), Reply,[method(post)]).
readTranspileFile(File, Reply):-http_post('http://localhost:9000/transpileCode', atom('application/json', File), Reply,[method(post)]).