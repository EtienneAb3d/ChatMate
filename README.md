# ChatMate
Java application to automate ChatGPT queries on a set of files.

# Use case example: create an SRT subtitle translator to Chinese

## SRT translation problem

SRT file translation may be complex because of possible bad sentence cut, making each text part loosing its context.

ChatGPT, with its ability to handle complex processing, by translating the SRT at once as a whole, can produce much better SRT translations than other available translation tools.

## Run ChatMate

Use the ChatMate Windows Exe release,

or

From de **distrib** folder, use the launcher for your machine (Java or OpenJDK must be installed).

![image](https://github.com/EtienneAb3d/ChatMate/assets/25932245/63c6e353-21bd-401e-b02f-bd0c104d737e)


## Configure

1- Define a *Config* name, like **SRTtoZH**<br/>
2- Define a *Suffix* to add to processed file names, like **-ZH**<br/>
3- Define a *Model* to use, like **gpt-3.5-turbo**<br/>
4- Enter a valid ChatGPT *Key*<br/>
5- Define a *System* prompt, like:<br/>
**Translate all text in Chinese keeping the SRT subtitle format with the sentence cut at best for each numbered section of the original.**

![image](https://github.com/EtienneAb3d/ChatMate/assets/25932245/47c35a2e-86c6-40bc-87f8-c95c86768f42)


## Test

1- Copy/Paste a SRT content as a User prompt<br/>
2- Click on the **Test** button<br/>
3- After the time needed to process the content, the result should appear on the right<br/>

![image](https://github.com/EtienneAb3d/ChatMate/assets/25932245/e08ceda1-579c-4c17-9f07-78d6fe08e950)


## Batch

1- Drag and Drop a set of files on the File list on the bottom left<br/>
2- Click on the **Process all files** button<br/>
3- Each processed file appears on the right with the suffixed name<br/>

![image](https://github.com/EtienneAb3d/ChatMate/assets/25932245/2de97b7c-4691-4e3b-b663-2e2fd4126519)

# Linked projects

https://github.com/EtienneAb3d/karaok-AI<br/>
https://github.com/EtienneAb3d/WhisperHallu <br/>
https://github.com/EtienneAb3d/WhisperTimeSync<br/>
https://github.com/EtienneAb3d/NeuroSpell<br/>
https://github.com/EtienneAb3d/OpenNeuroSpell<br/>

<hr>
This tool is a demonstration of our know-how.<br/>
If you are interested in a commercial/industrial AI linguistic project, contact us:<br/>
https://cubaix.com
