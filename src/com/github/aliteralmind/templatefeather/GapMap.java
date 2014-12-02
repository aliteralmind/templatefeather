/*license*\
   Template Featherweight: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.templatefeather;
   import  java.util.NoSuchElementException;
   import  java.util.Objects;
   import  com.github.xbn.lang.Copyable;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.util.lock.AbstractOneWayLockable;
   import  com.github.xbn.util.lock.LockException;
   import  java.util.Arrays;
   import  java.util.Map;
   import  java.util.Set;
   import  java.util.TreeMap;
   import  java.util.TreeSet;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <p>Associates gap names and their fill texts.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://templatefeather.aliteralmind.com">{@code http://templatefeather.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/templatefeather">{@code https://github.com/aliteralmind/templatefeather}</a>
 **/
public class GapMap extends AbstractOneWayLockable implements Copyable  {
   private final boolean             isUnfillOk;
  	private final Map<String,String>  map       ;
  	private final GapCharConfig       charConfig;
  	private int filledCount;
   private Matcher nameMtchr;
   /**
      <p>Create a new instance with defaults.</p>

    * <p>Equal to
      <br> &nbsp; &nbsp; <code>{@link #GapMap(GapCharConfig, Resettable, Appendable) this}((new {@link com.github.aliteralmind.templatefeather.GapCharConfig#GapCharConfig() GapCharConfig}), {@link com.github.aliteralmind.templatefeather.Resettable Resettable}.{@link com.github.aliteralmind.templatefeather.Resettable#NO NO}, debugDest_ifNonNull)</code></p>
    */
   public GapMap(Appendable debugDest_ifNonNull)  {
      this((new GapCharConfig()), Resettable.NO, debugDest_ifNonNull);
   }
   /**
      <p>Create a new instance.</p>

    * @param  char_config  May not be {@code null}.
    * @param  resettable  Should {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#unfill(String) unfilling} a gap be allowed? Get with {@link #isUnfillOk() isUnfillOk}{@code ()}.
      2param  debugDest_ifNonNull  If non-{@code null}, this is where debugging output is written to. Get with {@link com.github.xbn.io.Debuggable#getDebugAptr() getDebugAptr}{@code ()}*.
    * @see  #GapMap(Appendable)
    * @see  #GapMap(GapMap, Appendable)
    */
   public GapMap(GapCharConfig char_config, Resettable resettable, Appendable debugDest_ifNonNull)  {
      Objects.requireNonNull(char_config, "char_config");
      filledCount = 0;
      map = new TreeMap<String,String>();
      nameMtchr = Pattern.compile("\\w+").matcher("");
      charConfig = char_config;
      try  {
         isUnfillOk = resettable.isYes();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(resettable, "resettable", null, rx);
      }
   }
   /**
      <p>Create a new and unfilled map from an existing one.</p>

    * @param  to_copy  May not be {@code null}, and all gaps must have already been added (<code>{@link com.github.xbn.util.lock.Lockable Lockable}.{@link com.github.xbn.util.lock.Lockable#isLocked() isLocked}()</code> must be {@code true}).
    * @exception  LockException  If {@code to_copy} is unlocked.
    * @see  #getObjectCopy()
    * @see  #GapMap(GapCharConfig, Resettable, Appendable)
    */
   public GapMap(GapMap to_copy, Appendable debugDest_ifNonNull)  {
      super(to_copy);
      if(!to_copy.isLocked())  {
         throw  new LockException("to_copy.isLocked() is false.");
      }
      filledCount = to_copy.getFilledCount();
      nameMtchr = Pattern.compile("\\w+").matcher("");
      isUnfillOk = to_copy.isUnfillOk();
      charConfig = to_copy.charConfig;

      map = new TreeMap<String,String>();
      Set<String> nameSet = to_copy.map.keySet();
      for(String name : nameSet)  {
         add(name);
      }
      lock();
   }
   /**
      <p>Is unfilling a gap allowed? This also implies if the template is {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#isResettable() resettable}.</p>

    * @return  {@code true} if <code>{@link com.github.aliteralmind.templatefeather.FeatherTemplate FeatherTemplate}.{@link com.github.aliteralmind.templatefeather.FeatherTemplate#unfill(String) unfill}</code> may be safely called. <b>Warning:</b> If this is {@code true} and {@linkplain com.github.aliteralmind.templatefeather.FeatherTemplate#setAutoRendererX(Appendable) auto-rendering} is active, unfilling a gap does not affect <i>already output</i> gaps.
    * @see  #GapMap(GapCharConfig, Resettable, Appendable)
    */
   public boolean isUnfillOk()  {
      return  isUnfillOk;
   }
   /**
      <p>Add multiple gaps.</p>

      <p>For each element, this calls <code>{@link #add(String) add}(<i>element</i>)</code>.</p>

    * @param  names  <i>Should</i> not be empty, and all its elements must be valid and unique gap names.
    */
   public void addAll(String... names)  {
      for(int i = 0; i < names.length; i++)  {
         try  {
            add(names[i]);
         }  catch(RuntimeException rx)  {
            throw new RuntimeException("Attempting add(names[" + i + "])", rx);
         }
      }
   }
   /**
      <p>Add a new gap.</p>

    * @param  name  May not be {@code null} or empty, must contain only letters, digits, and underscores, may not contain the {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getStart() start} or {@linkplain com.github.aliteralmind.templatefeather.GapCharConfig#getEnd() end} characters, and may not {@linkplain #contains(String) exist}.
    * @exception  LockException  If <code>{@link com.github.xbn.util.lock.Lockable Lockable}.{@link com.github.xbn.util.lock.Lockable#isLocked() isLocked}()</code> is {@code true}.
    * @exception  IllegalArgumentException  If the gap exists, or (is non-{@code null} but) contains no characters or invalid characters.
    * @see  #addAll(String...)
    */
   public void add(String name)  {
      ciLocked();

      Objects.requireNonNull(name, "name");
      if(contains(name))  {
         throw  new IllegalArgumentException("Gap named \"" + name + "\" already exists. Already added: " + Arrays.toString(map.keySet().toArray()));
      }

      int startEndCharIdx = charConfig.indexOfStartOrEndChar(name);
      if(!nameMtchr.reset(name).matches()  ||  startEndCharIdx != -1)  {
         throw  new IllegalArgumentException("name (\"" + name + "\") must be non-null, non-empty, and only contain letters, digits, and underscores, but may not contain the gap-start (" + charConfig.getStart() + ") and end (" + charConfig.getEnd() + ")  characters.");
      }
      map.put(name, null);
   }
   /**
      <p>Does a gap exist?.</p>

    * @param  name  <i>Should</i> not be {@code null} or empty.
    * @return  {@code true}  If the gap name exists.
    */
   public boolean contains(String name)  {
      return  map.containsKey(name);
   }
   /**
      <p>How many gaps have been filled so far?.</p>

    * @see  #getUnfilledCount()
    * @exception  LockException  If gaps are still being added.
    * @see  #lock()
    */
   public int getFilledCount()  {
      ciNotLocked();
      return  filledCount;
   }
   /**
      <p>How many gaps have yet to be filled?.</p>

    * @return  <code>({@link #size() size}() - {@link #getFilledCount() getFilledCount}())</code>
    */
   public int getUnfilledCount()  {
      return  (size() - getFilledCount());
   }
   /**
      <p>Prevent more gaps from being added, and allow fills.</p>

      <p>This calls <code>{@link com.github.xbn.util.lock.OneWayLockable super}.{@link com.github.xbn.util.lock.OneWayLockable#lock() lock}()</code></p>

    * @exception  TemplateFormatException  If no gaps were added.
    */
   public void lock()  {
      if(size() == 0)  {
         throw  new TemplateFormatException("No gaps added!");
      }
      super.lock();
   }
   /**
      <p>All gap names.</p>

    * @see  #filledNameSet()
    * @see  #unfilledNameSet()
    * @exception  LockException  If gaps are still being added.
    * @see  #lock()
    */
   public Set<String> newNameSet()  {
      ciNotLocked();
      Set<String> nameSet = new TreeSet<String>();
      nameSet.addAll(map.keySet());
      return  nameSet;
   }
   /**
      <p>All gap names that have been filled.</p>

    * @see  #unfilledNameSet()
    * @see  #getFilledCount()
    * @see  #newNameSet()
    */
   public Set<String> filledNameSet()  {
      Set<String> original = map.keySet();
      Set<String> filled = new TreeSet<String>();
      for(String name : original)  {
         if(isFilled(name))  {
            filled.add(name);
         }
      }
      return  filled;
   }
   /**
      <p>All gap names that have not been filled.</p>

    * @see  #filledNameSet()
    * @see  #getUnfilledCount()
    * @see  #newNameSet()
    */
   public Set<String> unfilledNameSet()  {
      Set<String> original = map.keySet();
      Set<String> unfilled = new TreeSet<String>();
      for(String name : original)  {
         if(!isFilled(name))  {
            unfilled.add(name);
         }
      }
      return  unfilled;
   }
   void fill(String name, Object text)  {
      ciNotLocked();
      String s = null;
      if(text == null)  {
         if(!isUnfillOk())  {
            throw  new TemplateResettableException("name=\"" + name + "\"");
         }
      }  else if(isFilled(name))  {
         throw  new GapFilledException("name=\"" + name + "\". " + toString());
      }  else  {
         s = text.toString();
      }
      map.put(name, s);
      filledCount += ((text != null) ? 1 : -1);
   }
   /**
      <p>Is every gap filled?.</p>

    * @return  <code>({@link #getUnfilledCount() getUnfilledCount}() == 0)</code>
    */
   public boolean isFilled()  {
      return  (getUnfilledCount() == 0);
   }
   /**
      <p>Is a gap filled?.</p>

    * @param  name  Must {@linkplain #contains(String) exist}.
    * @exception  NoSuchElementException  If the gap doesn't exist.
    * @see  #getUnfilledCount()
    * @see  #getFilledCount()
    */
   public boolean isFilled(String name)  {
      if(!contains(name))  {
         throw  new NoSuchElementException("name=\"" + name + "\". " + toString());
      }
      return  (map.get(name) != null);
   }
   /**
      <p>The number of gaps.</p>

    * @return  The number of times {@link #add(String) add} was called.
    */
   public int size()  {
      return  map.size();
   }
   void unfill()  {
      Set<String> nameSet = map.keySet();
      for(String name : nameSet)  {
         if(isFilled(name))  {
            fill(name, null);
         }
      }
   }
   /**
      <p>The text to replace the gap with.</p>

    * @exception  GapFilledException  If the gap is not {@linkplain #isFilled(String) filled}.
    */
   public String getFillText(String name)  {
      if(!isFilled(name))  {
         throw  new GapUnfilledException("name=\"" + name + "\". " + toString());
      }
      return  map.get(name);
   }
   public String toString()  {
      return  "filled=" + Arrays.toString(filledNameSet().toArray()) + ", unfilled=" + Arrays.toString(unfilledNameSet().toArray());
   }
   /**
      <p>Get a duplicate of this <code>GapMap</code>.</p>

    * @return <code>({@link #GapMap(GapMap, Appendable) GapMap}(this, null))</code>
    */
   public GapMap getObjectCopy()  {
      return  (new GapMap(this, null));
   }
}
