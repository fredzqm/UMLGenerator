import sys
from os import listdir
from os.path import isdir

def getJavaFiles(direct):
    """This function takes in the path to the java directory in a project and returns the names of all the java files"""
    mylist = listdir(direct)
    ret = ''
    for dir in mylist:
        if isdir(direct + '/' + dir):
            ret = ret + getJavaFiles(direct + '/' + dir)
        else:
            ext = dir.split(".")[1]
            if ext == "java":
                folders = direct.split("/")
                ret = ret + ",\""
                i = 0
                for folder in folders:
                    if i != 0:
                        ret = ret + folder + "."
                    i = i + 1
                ret = ret + dir.split(".")[0] + "\""
            else:
                print(dir)
    return ret

def main():
    ret = ''
    ret = getJavaFiles(sys.argv[1])
    print(ret)

if __name__ == "__main__": main()