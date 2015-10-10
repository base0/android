# there are 9 solution
# but only one solution that n00 = 4

def solve():
  for n00 in range(10):
    for n01 in range(10):
      if n00+n01-9 == 4:
        for n10 in range(10):
          for n11 in range(10):
            for n12 in range(10):
              if (n10-n11)*n12 == 4:
                for n20 in range(1,10):
                  for n21 in range(10):
                    for n22 in range(10):
                      if n20+n21-n22 == 4 and \
                         (n00+n10)/n20 == 4 and \
                         (n01-n11)*n21 == 4 and \
                         9-n12-n22 == 4:
                           print(n00, n01)
                           print(n10, n11, n12)
                           print(n20, n21, n22)
                           return
solve()
