import sys
import os.path
from os import listdir

def getJavaFiles(direct):
    """
        This function takes in the path to the java directory in a project and returns the names of all the java files
    """
    mylist = listdir(direct)
    ret = ''
    for dir in mylist:
        if os.path.isdir(direct + '/' + dir):
            ret = ret + getJavaFiles(direct + '/' + dir)
        else:
            nameSplit = os.path.splitext(dir)
            if nameSplit[1] == '.java':
                folders = os.path.split(direct)
                qualifiedName = ''
                for k in range(1, len(folders)):
                    qualifiedName += folders[k] + '.'

                ret = ret + "\"{0}{1}\", ".format(qualifiedName, nameSplit[0])
            else:
                print(dir)
    return ret[:-1]

def main():
    ret = getJavaFiles(sys.argv[1])
    print(ret)

if __name__ == "__main__":
    main()
