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

**1: From Repo clone in Localhost: (for Windows users)**
1. Go to deliverables and download PSP21.jar
2. Download form windows a terminal that support unicode:  https://www.microsoft.com/store/productId/9N0DX20HK701?ocid=pdpshare  
3. Open the `Windows Terminal` that you have just downloaded, and navigate to C:/[dir]/[folder with the jar files]  
4. From here, just type in the `Windows terminal`:  
   -> `java -jar PSP21.jar` (to run the clients and servers)<br>

`**2: From two different computers(Repo clone): (for Windows users)**
1. Go to deliverables and download PSP21.jar
2. disable the firewall( win key -> windows Defender Firewall -> activate/deactivate fireWall -> disable windows defender (both public and private network)
3. create an account and install https://www.zerotier.com/, for the use of zero teir consult the guide https://docs.zerotier.com/start
4. Download form windows a terminal that support unicode:  https://www.microsoft.com/store/productId/9N0DX20HK701?ocid=pdpshare  
5. Open the `Windows Terminal` that you have just downloaded, and navigate to C:/[dir]/[folder with the jar files]  
6. From here, just type in the `Windows terminal`:  
   -> `java -jar PSP21.jar` (to run the clients and servers)<br>

`**NB:**
The application automatically saves the history of the games, in a path specified when running the server. To delete them, it has to be done manually


   ![1871216](https://github.com/Diego41ITA/ing-sw-2024-quattrone-galatea-lamperti-lodetti/assets/161478338/00292629-7280-4084-bc3a-eff32d2175e7)
