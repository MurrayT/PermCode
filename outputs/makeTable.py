import argparse
import collections
import re

def get_chunk(file_handle):
    chunk = collections.deque()
    line = file.readline()
    while line:
        line = line.strip()
        if line == "==========":
            return chunk
        else:
            chunk.append(line)
        line = file.readline()

def get_patt(chunk):
    pattline = chunk.popleft()
    pattstr = pattline.split("=")[-1]
    if pattstr == '':
        pattstr = 'ε'
    return pattstr

def get_basis(chunk, *args):
    basis = []
    bit = chunk.pop()
    while not re.match(r'\d+ basis elements', bit):
        if bit not in args:
            basis.append(bit)
        bit = chunk.pop()
    return sorted(basis)

def has_chunk(file):
    pos = file.tell()
    line = file.readline()
    while line:
        if line.strip() == "==========":
            file.seek(pos)
            return True
        line=file.readline()
    else:
        file.seek(pos)
        return False


parser = argparse.ArgumentParser(description="Take multiple files and parse them into a single table")
parser.add_argument('files', metavar='F', type=open, nargs='+')

args = parser.parse_args()

while any(map(has_chunk, args.files)):
    current_chunk_id = None
    for file in args.files:
        chunk = get_chunk(file)
        pattstr = get_patt(chunk)
        if current_chunk_id:
            if not current_chunk_id == pattstr:
                print("chunk mismatch got {}, expected current_chunk_id {}".format(pattstr, current_chunk_id))
                continue
        else:
            current_chunk_id = pattstr
            print(pattstr, end=';')
        basis = get_basis(chunk)
        print(",".join(basis), end=';')

    else:
        print()
        current_chunk_id = None
