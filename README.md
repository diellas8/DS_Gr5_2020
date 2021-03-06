## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Additional Info](#additional-info)


## General info
This project contains simple encryption tecniques.



## Technologies
 This project is created with:
* Java JDK 13


## Additional Info
##### Database and tokens

 
For this project we have used a sqlite database named projekti_siguri with the following fields:
name, salt, hashed_password, priv_key_path and pub_key_path.
When a user is created the password is hashed with the SHA-512 algorithm and 16 random bytes are generated
as salt. Both are encoded with Base64 and saved into the database.
The path for the pem files containing the RSA keys are saved into the database aswell.

   
The tokens generated when the user logins are JWT tokens that are signed with the user's private RSA key using 
the RS256 algorithm. The tokens are issued from DS_Gr5_2020 and the name of the user is saved into the subject.
The tokens expire 20 minutes after being created.   

<pre>
 use   java ds beale encrypt < book > < plaintext >    or   java ds beale decrypt < book > < ciphertext >
  With this method, each letter in the secret message is replaced with a number which represents
 the position of a word in the book which starts with this letter.   
Example:
       In this example we've used a file that contains the following text " the quick brown fox
        jumps over the lazy dog."
       Plaintext: pershendetje nga fiek
       Ciphertext: 24 3 12 25 2 3 15 41 3 1 21 3 4 15 43 37 4 17 7 3 9



                 
Caesar Cipher
 use    java ds caesar encrypt < key >  < plaintext >   or    java ds caesar decrypt < key > < ciphertext >
   or  java ds caesar bruteForce < plaintext >
Each letter is shifted along in the alphabet by the same number of letters.
Example:
        In this example, each letter in the plaintext message has been shifted 4 letters down in the alphabet.
        Plaintext: pershendetje nga fiek
        Ciphertext: tivwlirhixni rke jmio
        
        
        
        
        
Playfair Cipher
 use   java  ds playfair encrypt < key >  or    java  ds playfair decrypt < key > < ciphertext >       
add --table if you want the table to be shown
 The Playfair cipher uses a 5 by 5 table containing a key word or phrase. To generate the table, one would first
 fill in the spaces of the table with the letters of the keyword (dropping any duplicate letters), then fill the
 remaining spaces with the rest of the letters of the alphabet in order (to reduce the alphabet to fit you can either 
 omit "Q" or replace "J" with "I")
  To encrypt a message, one would break the message into groups of 2 letters.
    1. If both letters are the same, add an X between them. Encrypt the new pair, re-pair the remining letters and
       continue.
    2. If the letters appear on the same row of your table, replace them with the letters to their immediate right
      respectively, wrapping around to the left side of the row if necessary. For example, using the table above, 
      the letter pair GJ would be encoded as HF.
    3. If the letters appear on the same column of your table, replace them with the letters immediately below,
      wrapping around to the top if necessary. For example, using the table above, the letter pair MD would be encoded
       as UG.
    4. If the letters are on different rows and columns, replace them with the letters on the same row respectively
     but at the other pair of corners of the rectangle defined by the original pair. The order is important - the first
     letter of the pair should be replaced first. For example, using the table above, the letter pair EB would be 
     encoded as WD. 
 Example:
        In this example we build the table with the keyword "topi". 
        Plaintext: pershendetje nga fiek
        Ciphertext:id su lc rb bi el vn fm el rp
        
create-user 
use ds create-user < name >  
The create-user command is used to generate a pair of RSA keys and saves them in PEM files. 

delete-user
use ds delete-user < name >
The delete-user command is used to remove existing keys of the user who is specified.

export-key
use ds export-key < public | private > < name > [file]
The argument < public | private > determines what type of key will be exported.
The argument < name > determines which user to export the key.
The [file] argument is optional and it gives the path of the file where the exported key will be saved.
If file is not specified then the key will be showed in the console.

import-key
use ds import-key < name > < path >
Imports a private or public key and saves it in the keys directory.
The < name > argument specifies the name of the key that is saved in the directory.
The < path > argument specifies the path of the imported key.
If the key is private it will automatically generate the public key and will save it. 

write-message
use ds write-message < name > < message > [ file ]
The < name > argument is the receiver of the message.
The < message > is the text that will be encrypted
< file > is optional and gives the path where the encrypted message will be saved.

read-message
use ds read-message < encrypted-message >
< encrypted-message > is the message we want to decrypt.



</pre>        
