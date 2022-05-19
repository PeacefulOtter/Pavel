<p>
  Pavel is a project aimed at implementing highly performant operations like 
  <code>map</code>
  , 
  <code>filter</code>
  , 
  <code>reduce</code>
   on Scala Lists
</p>
<p>
  Supports:
</p>
<pre><code class="nohighlight">
  <span>.</span>
  <span class="identifier">map</span>
  <span>[</span>
  <span class="type-name">U</span>
  <span>]( </span>
  <span class="identifier">pred</span>
  <span>: (</span>
  <span class="identifier">elt</span>
  <span>: </span>
  <span class="type-name">T</span>
  <span>, </span>
  <span class="identifier">i</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>) =&gt; </span>
  <span class="type-name">U</span>
  <span> )
  .</span>
  <span class="identifier">filter</span>
  <span>( </span>
  <span class="identifier">pred</span>
  <span>: (</span>
  <span class="identifier">elt</span>
  <span>: </span>
  <span class="type-name">T</span>
  <span>, </span>
  <span class="identifier">i</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>) =&gt; </span>
  <span class="type-name">Boolean</span>
  <span> )
  .</span>
  <span class="identifier">reduce</span>
  <span>[</span>
  <span class="type-name">U</span>
  <span>]( </span>
  <span class="identifier">pred</span>
  <span>: (</span>
  <span class="identifier">acc</span>
  <span>: </span>
  <span class="type-name">U</span>
  <span>, </span>
  <span class="identifier">cur</span>
  <span>: </span>
  <span class="type-name">T</span>
  <span>) =&gt; </span>
  <span class="type-name">U</span>
  <span>, </span>
  <span class="identifier">u</span>
  <span>: </span>
  <span class="type-name">U</span>
  <span> )
  .</span>
  <span class="identifier">collect</span>
  <span>()
  .</span>
  <span class="identifier">print</span>
  <span>()</span>
</code></pre>
<h1 id="usage" class="title">Usage</h1>

<h5 id="example-with-map---filter---map---collect" class="section">Example with map - filter - map - collect</h5>
<pre><code class="nohighlight">
  <span class="keyword">import</span>
  <span> </span>
  <span class="identifier">pavelist</span>
  <span>.</span>
  <span class="type-name">PavelEmpty</span>
  <span>
  
  </span>
  <span class="keyword">val</span>
  <span> </span>
  <span class="identifier">myList</span>
  <span>: </span>
  <span class="type-name">List</span>
  <span>[</span>
  <span class="type-name">Int</span>
  <span>] = </span>
  <span class="type-name">List</span>
  <span>(</span>
  <span class="number-literal">1</span>
  <span>, </span>
  <span class="number-literal">2</span>
  <span>, </span>
  <span class="number-literal">3</span>
  <span>, </span>
  <span class="number-literal">4</span>
  <span>, </span>
  <span class="number-literal">5</span>
  <span>)
  </span>
  <span class="keyword">val</span>
  <span> </span>
  <span class="identifier">pavelList</span>
  <span> = </span>
  <span class="type-name">PavelEmpty</span>
  <span>(</span>
  <span class="identifier">myList</span>
  <span>)
      .</span>
  <span class="identifier">map</span>
  <span>((</span>
  <span class="identifier">elt</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>, </span>
  <span class="identifier">i</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>) =&gt; </span>
  <span class="identifier">elt</span>
  <span> * </span>
  <span class="number-literal">3</span>
  <span>)
      .</span>
  <span class="identifier">filter</span>
  <span>((</span>
  <span class="identifier">elt</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>, </span>
  <span class="identifier">i</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>) =&gt; (</span>
  <span class="identifier">elt</span>
  <span> % </span>
  <span class="number-literal">2</span>
  <span>) == </span>
  <span class="number-literal">0</span>
  <span>)
      .</span>
  <span class="identifier">map</span>
  <span>((</span>
  <span class="identifier">elt</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>, </span>
  <span class="identifier">i</span>
  <span>: </span>
  <span class="type-name">Int</span>
  <span>) =&gt; </span>
  <span class="identifier">elt</span>
  <span> / </span>
  <span class="number-literal">2</span>
  <span>)
      .</span>
  <span class="identifier">collect</span>
  <span>()</span>
</code></pre>