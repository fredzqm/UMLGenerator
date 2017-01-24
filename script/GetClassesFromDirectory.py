import sys
import os.path
from os import listdir

def getJavaFiles(direct):
    """
        This function takes in the path to the java directory in a project and returns the names of all the java files
    """
    mylist = listdir(direct)
    ret = ''
    for mdir in mylist:
        if os.path.isdir(direct + '/' + mdir):
            ret = ret + getJavaFiles(direct + '/' + mdir)
        else:
            nameSplit = os.path.splitext(direct + '/' + mdir)
            #print(nameSplit)
            #print(nameSplit)
            if nameSplit[1] == '.java':
                path = os.path.split(nameSplit[0])
                folders = path[0].split("/")
                qualifiedName = ''
                for k in range(1, len(folders)):
                    qualifiedName += folders[k] + '.'

                ret = ret + "\"{0}{1}\", ".format(qualifiedName, path[1])
            else:
                print(mdir)
    return ret[:-1]

def main():
    ret = getJavaFiles(sys.argv[1])
    print(ret)

if __name__ == "__main__":
    main()
