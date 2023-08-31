map_build([], "NULL", Ind) :- true, !.
map_build(ListMap, Res) :- retractall(element(X, Y)), map_build(ListMap, Res, 0), !.
inc(A, B) :-
 B is A + 1.
map_build(ListMap, node(Elem, TL, TR), Ind) :- length(ListMap, L),
	get_med(ListMap, L, Elem, Left, Right, Ind),
	L2 is floor((L - 1) / 2), 
	build(L2, Ind, TL), 
	NI is Ind + floor((L + 1) / 2),
	map_build(Right, TR, NI), !.																																													
build(1, Ind, node(Elem, "NULL", "NULL")) :- 
element(Ind, Elem), 
!.
build(1, Ind, "NULL") :- \+ element(Ind, Elem), !.
build(X, Ind, "NULL") :- XX is X, XX < 1, !.
build(Size, Ind, node(Elem, TL, TR)) :- Index is Ind + floor((Size - 1) / 2),
	element(Index, Elem), 
	S1 is floor((Size - 1) / 2), S2 is floor((Size) / 2), 
	inc(Index, I2), 
	build(S2, I2, TR),
	build(S1, Ind, TL),						
	!.																													
map_get(node((Key, Elem), TL, TR), Key, Elem) :- true, !.
map_get(node((K, E), TL, TR), Key, Elem) :- K > Key, map_get(TL, Key, Elem), !.
map_get(node((K, E), TL, TR), Key, Elem) :- K =< Key, map_get(TR, Key, Elem), !.
get_med([H | T], Size, H, [], T, Ind) :- S2 is (floor(Size / 2)), length(T, S2),  !.
get_med([H | T], Size, R, [H | Left], Right, Ind) :- asserta(element(Ind, H)), inc(Ind, NI),  get_med(T, Size, R, Left, Right, NI), !.
map_keys("NULL", []) :- true, !.
map_keys(node((Key, Elem), TR, TL), Keys) :- map_keys(TR, KR), !, map_keys(TL, KL), append(KR, [Key | KL], Keys).

