import pandas as pd
import numpy as np

p = pd.read_json("leetcode.json")
diff = np.array(pd.DataFrame(p, columns=['difficulty'])).flatten()
id = np.array(pd.DataFrame(p, columns=['frontendQuestionId'])).flatten()
title = np.array(pd.DataFrame(p, columns=['titleCn'])).flatten()
slug = np.array(pd.DataFrame(p, columns=['titleSlug'])).flatten()

str = '","'
t = '"'+str.join(diff)+'"'
print(t)
# with open("title.txt", 'w') as f:
#     f.write(t)
#
# with open("info.txt", "w", encoding='utf-8') as f:
#     for i in range(50):
#         f.write(f'<string name="{title[i]}"></string>\n')

