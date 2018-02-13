SongLib CS213
Author: Ruoyang Hu & Jianan Yu
Date: 2/12/2018

//TODO:
-Check again about case insensitive(i.e. edit,add).
(***)case insensitive: means Strawberry Field = strawberry field
-Check sorted display(after add, after edit): alphabetical order of names, (same name, alphabetical order to artists).

-edit:
 -if name or artist is null, cannot save, error pop up
 -if name and artist becomes similar to another song in the lib, error pop up
 -However, if name or artist are as same as the song that is being editing, only change other propertities.
 -if the edit is valid, ask for confirm, if confirmed, change property of song object.
-add finish (check method?).

-duplicate ERROR or COMFIRMATION?
 -clean duplicate code.
 -Clean Edit and Add Method.
 -Check load and save function with Edit and Add mothod with possible exceptions.

-load and save:
 -use print and read(BufferedReader&BufferedWriter).
 -(NO)Do we need a fileChooser System?

//Test Cases:
(Assumption)