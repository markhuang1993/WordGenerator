update word ftl: run test -> get xxxForm.doc -> change name to xxxForm.xml -> use idea structure view -> add new text in ftl
gen jar: use profile gen-jar -> clean package -DskipTests
main method args:
    "C:\Users\markh\IdeaProjects\WordGenerator\src\main\resources\dummy\diff.txt"                           <-- dummy diff txt
    "C:\Users\markh\IdeaProjects\WordGenerator\src\main\resources\dummy\config_yml\global\g_config.yml"     <-- dummy global config
    "C:\Users\markh\IdeaProjects\WordGenerator\src\main\resources\dummy\config_yml\local\l_config.yml"      <-- dummy local config
    "Mark"                                                                                                  <-- jenkins executor name
    "C:\Users\markh\Pictures\Uplay"                                                                         <-- form output dir