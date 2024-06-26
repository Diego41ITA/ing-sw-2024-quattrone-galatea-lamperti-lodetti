# IngSW2024

<h4 align="center">A distributed version of the game Codex Naturalis made by


  ### Diego Quattrone
  
  ### Lorenzo Galatea
  
  ### Luca Lamperti
  
  ### Alessandro Lodetti

  ## Coverage:
In addition to the game logic which is fully implemented in the model,
we decided also to test the controller.
Not all of the controller was testable as some parts required user input


  ![image](https://github.com/Diego41ITA/ing-sw-2024-quattrone-galatea-lamperti-lodetti/assets/161478338/3949c2c8-5fef-459f-be45-e1aa767ba7b2)

</div>


## What we have implemented

We have `implemented`, in addition to the `Game Specific` and `Game Agnostic` requirements, the following advanced features:
   | Feature | Implemented  |
|:--------|:----|
| Multiple Games   | :heavy_check_mark:    |
| Disconnection resilience: | :heavy_check_mark:    |
| Socket and RMI  | :heavy_check_mark:    |
| Complete rules  | :heavy_check_mark:    |
| TUI + GUI  | :heavy_check_mark:    |
| Persistence:  | ✔️  |

![Voto](https://github.com/Diego41ITA/ing-sw-2024-quattrone-galatea-lamperti-lodetti/assets/161478338/a7894c9b-1df6-4e71-8e9d-ecceb22609cd)

# How to Use(SET UP GUIDE)

**1: Playing locally, on a single computer:**
1. Go to "deliverables" folder and download PSP21.jar
2. Ensure to have a terminal that supports UTF-8 encoding and ansi code. The default one on MacOS system should do it, for Windows users we suggest to download this one: https://www.microsoft.com/store/productId/9N0DX20HK701?ocid=pdpshare, and to manually set the UTF-8 encoding. 
3. Open the previously described terminal and navigate to the folder where the jar has been saved, tipically the path is something like "C:/[dir]/[folder with the jar files]"  
4. From here, just type in the terminal:  
   -> `java -jar PSP21.jar` (to run both the clients and the servers)<br>

`**2: Playing on different computers:**
1. Go to "deliverables" folder and download PSP21.jar
2. Ensure to allow to enable incoming and outgoing connections on the port you want to connect to. Alternatively, for Windows Users it's possible to temporarly disable the firewall( win key -> windows Defender Firewall -> activate/deactivate fireWall -> disable windows defender (both public and private network)
3. We suggest to use "ZeroTier", by creating an account and installing the application https://www.zerotier.com/; for more information about its correct usage, consult the guide https://docs.zerotier.com/start
4. Ensure to have a terminal that supports UTF-8 encoding and ansi code. The default one on MacOS system should do it, for Windows users we suggest to download this one: https://www.microsoft.com/store/productId/9N0DX20HK701?ocid=pdpshare, and to manually set the UTF-8 encoding. 
5. Open the previously described terminal and navigate to the folder where the jar has been saved, tipically the path is something like "C:/[dir]/[folder with the jar files]"  
6. From here, just type in the `Windows terminal`:  
   -> `java -jar PSP21.jar` (to run the clients and servers)<br>

`**NB:**
1. The application automatically saves the history of the games, in a path specified when running the server. To delete them, it has to be done manually
2. Due to copyright reasons, the graphical resources necessary for displaying the GUI have not been pushed to this repository. Therefore, to successfully build the jar file on your pc, by cloning the project's repository, you need to download the 'images' folder from the #1-general channel on Slack. Extract its contents and then move the uncompressed 'images' folder into the resources/scenes directory.


   ![1871216](https://github.com/Diego41ITA/ing-sw-2024-quattrone-galatea-lamperti-lodetti/assets/161478338/00292629-7280-4084-bc3a-eff32d2175e7)
