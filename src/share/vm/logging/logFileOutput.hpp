/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */
#ifndef SHARE_VM_LOGGING_LOGFILEOUTPUT_HPP
#define SHARE_VM_LOGGING_LOGFILEOUTPUT_HPP

#include "logging/logFileStreamOutput.hpp"
#include "runtime/mutex.hpp"
#include "utilities/globalDefinitions.hpp"

class LogDecorations;

// The log file output, with support for file rotation based on a target size.
class LogFileOutput : public LogFileStreamOutput {
 private:
  static const char*  FileOpenMode;
  static const char*  FileCountOptionKey;
  static const char*  FileSizeOptionKey;
  static const char*  PidFilenamePlaceholder;
  static const char*  TimestampFilenamePlaceholder;
  static const char*  TimestampFormat;
  static const size_t StartTimeBufferSize = 20;
  static const size_t PidBufferSize       = 21;
  static char         _pid_str[PidBufferSize];
  static char         _vm_start_time_str[StartTimeBufferSize];

  Mutex _rotation_lock;
  const char* _name;
  char* _file_name;
  char* _archive_name;

  uint  _current_file;
  uint  _file_count;
  uint  _file_count_max_digits;

  size_t  _archive_name_len;
  size_t  _rotate_size;
  size_t  _current_size;

  void archive();
  bool configure_rotation(const char* options);
  char *make_file_name(const char* file_name, const char* pid_string, const char* timestamp_string);
  static size_t parse_value(const char* value_str);

  bool should_rotate(bool force) {
    return is_rotatable() &&
             (force || (_rotate_size > 0 && _current_size >= _rotate_size));
  }

 public:
  LogFileOutput(const char *name);
  virtual ~LogFileOutput();
  virtual bool initialize(const char* options);
  virtual int write(const LogDecorations& decorations, const char* msg);

  virtual bool is_rotatable() {
    return LogConfiguration::is_post_initialized() && (_file_count > 0);
  }

  virtual void rotate(bool force);

  virtual const char* name() const {
    return _name;
  }

  static void set_file_name_parameters(jlong start_time);
};

#endif // SHARE_VM_LOGGING_LOGFILEOUTPUT_HPP
